import sys
from interface import Server
import socket
import multiprocessing as mp


def pcs_func(conn:socket.socket, server:Server):
    
    data = conn.recv(1024)
    text = str(data.decode())

    if "preleva" in text:
        result = server.preleva()
        
    else:
        tokens = text.split("-")
        server.deposita(int(tokens[1]))
        result = "depositato"


    conn.send( (str(result) + "\n").encode())
    conn.close()






class ServerSkeleton(Server):

    def __init__(self,port):
        self.port = port


    def run_Skeleton(self):

        IP = 'localhost'

        s = socket.socket(family=socket.AF_INET, type= socket.SOCK_STREAM)
        s.bind((IP, self.port))
        s.listen(1)

        while True:
            conn, addr = s.accept()

            p = mp.Process(target=pcs_func, args=(conn, self))
            p.start()

    

class ServerImpl(ServerSkeleton):

    def __init__(self, port, queue:mp.Queue):
        super().__init__(port)
        self.queue = queue

    def deposita(self, id: int):
        self.queue.put(id)
        print(f"[SERVER] ha depositato {id}")

    def preleva(self):
        id = self.queue.get()
        print(f"[SERVER] ha prelevato {id}")
        return id



if __name__ == "__main__":

    try:
        PORT = sys.argv[1]
    except IndexError:
        print("Specify port")

    queue = mp.Queue(5)

    server = ServerImpl(int(PORT), queue)
    server.run_Skeleton()
