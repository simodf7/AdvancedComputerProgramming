import socket
import sys
import threading
from interface import Counter


def thread_function(conn, self):
    data = conn.recv(1024)

    print(str(data.decode()))
    tokens = str(data.decode()).split("-")
    if(tokens[0] == "setCount"):
        id = tokens[1]
        s = int(tokens[2])
        self.setCount(id,s)
        conn.close()
    elif(tokens[0] == "sum"):
        s = int(tokens[1])
        valore = self.sum(s)
        conn.send(str(valore).encode("utf-8"))
        conn.close()
    else:
        valore = self.increment()
        conn.send(str(valore).encode("utf-8"))
        conn.close()



class Skeleton(Counter):
    
    def __init__(self, port):
        self.port = port
        self.count = 0

    def setCount(self, id, s):
        pass

    def sum(self, s):
        pass

    def increment(self):
        pass

    def run_skeleton(self):

        host = 'localhost'
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.bind((host, self.port))
        
        s.listen(5)

        while True:
            conn, addr = s.accept()
            print("Connected to: ", addr[0] , ":", addr[1])

            t = threading.Thread(target=thread_function, args=(conn,self))
            t.start()

        s.close()




class Impl(Skeleton):

    def setCount(self, id, s):
        self.count = s 
        print("Count modified after set by ", id, " now is ", self.count)

    def sum(self, s):
        self.count += s 
        print("Count modified after sum now is ", self.count)
        return self.count

    def increment(self):
        self.count += 1 
        print("Count modified after increment now is ", self.count)
        return self.count



if __name__ == "__main__":

    try:
        PORT = sys.argv[1]
    except IndexError:
        print("Please the port")

    realCounter = Impl(int(PORT))
    realCounter.run_skeleton()