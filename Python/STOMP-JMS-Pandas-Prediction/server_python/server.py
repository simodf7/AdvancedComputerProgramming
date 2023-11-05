import sys
from interface import Service
import socket 
import multiprocessing as mp
import csv
import pandas as pd
from scipy import stats


def prc_func(conn:socket.socket, server:Service):

    data = conn.recv(1024) 

    text = str(data.decode())

    print(text)

    if "forecast" in text:
        print("OKAY")
        year = text.split("-")[1]
        result = server.forecast(year)
        msg = result + "\n" 
        conn.send(msg.encode())
        print("[SERVER] Sent result to dispacther")


   
    conn.close() 





class ServerSkeleton(Service):

    def __init__(self, port):
        self.port = port 

    def run_Skeleton(self):
        IP = "localhost"

        sock = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
        sock.bind((IP, self.port))

        sock.listen(1)
        print("[SERVER] IN ASCOLTO")

        while True:
            conn,addr = sock.accept() 

            p = mp.Process(target=prc_func, args=(conn, self))
            p.start()
        

class ServerImpl(ServerSkeleton):

    def __init__(self, port):
        super().__init__(port) 

    def forecast(self, year: int):
        
        nyc = pd.read_csv("USH00305801-tmax-1-1-1895-2022.csv", skiprows=4)
        nyc.columns = ['Date', 'Temperature', 'Anomaly']
        nyc.Date = nyc.Date.floordiv(100) 

        linear_regression = stats.linregress(x=nyc.Date, y=nyc.Temperature)
        prediction = linear_regression.slope * int(year) + linear_regression.intercept

        print(f"[Server] Predicted {prediction} for {year} ")

        result = "prediction-" + str(prediction)

        return result


            
if __name__ == "__main__":
    try:
        PORT = sys.argv[1]
    except IndexError:
        print("Specify port")
    
    server = ServerImpl(int(PORT)) 
    server.run_Skeleton()

