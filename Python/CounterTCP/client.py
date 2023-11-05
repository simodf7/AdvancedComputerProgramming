import socket
import sys
from interface import Counter


class Proxy(Counter):
    
    def __init__(self, port):
        self.port = port
    
    def setCount(self, id, so):
        IP = 'localhost'
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((IP, self.port))
        
        msg = "setCount-" + id + "-" + str(so)
        s.send(msg.encode("utf-8"))
        print("[CLIENT] Sent set message to server")

        s.close()


    def sum(self,so):
        IP = 'localhost'
        BUFFER_SIZE = 1024
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((IP, self.port))
        
        msg = "sum-" + str(so)
        s.send(msg.encode("utf-8"))
        print("[CLIENT] Sent sum message to server")

        valore = s.recv(BUFFER_SIZE)
        
        print("Received data " + valore.decode("utf-8"))

        s.close()

    def increment(self):
        print("ENTRATO")
        IP = 'localhost'
        BUFFER_SIZE = 1024
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((IP, self.port))
        
        msg = "increment"
        s.send(msg.encode("utf-8"))
        print("[CLIENT] Sent increment message to server")

        valore = s.recv(BUFFER_SIZE)
        print("Received data " + valore.decode("utf-8"))

        s.close()



if __name__ == "__main__":
    
    try:
        PORT = sys.argv[1]
    except IndexError:
        print("Specify args")

    proxy = Proxy(int(PORT))
    proxy.increment()
    proxy.setCount("3", 5)
    proxy.sum(4)
