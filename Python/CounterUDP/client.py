# client.py

from interface import Counter
import socket, sys

class Proxy(Counter):
    
    def __init__(self, port):
        self.port = port

    def setCount(self, id, s):

        IP = "localhost"
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        
        msg = "setCount#" + id + "#" + str(s)
        sock.sendto(msg.encode("utf-8"), (IP.encode("utf-8"), self.port))


    def sum(self, s):

        IP = "localhost"
        BUFFER_SIZE = 1024
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        
        msg = "sum#" + str(s)
        sock.sendto(msg.encode("utf-8"), (IP.encode("utf-8"), self.port))

        msgServer, addr = sock.recvfrom(BUFFER_SIZE)
        print("[CLIENT] Message received from server after sum: " , msgServer.decode("utf-8"))

    def increment(self):

        IP = "localhost"
        BUFFER_SIZE = 1024
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        
        msg = "increment"
        sock.sendto(msg.encode("utf-8"), (IP.encode("utf-8"), self.port))

        msgServer, addr = sock.recvfrom(BUFFER_SIZE)
        print("[CLIENT] Message received from server after increment: " , msgServer.decode("utf-8"))

        

if __name__ == "__main__":
    try:
        PORT = sys.argv[1]
    except IndexError:
        print("Please specify port")
    
    assert PORT != "", 'specify port'
    
    proxy = Proxy(int(PORT))
    proxy.setCount("3", 19)
    proxy.sum(5)
    proxy.increment()