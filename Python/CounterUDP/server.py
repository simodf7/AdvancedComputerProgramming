#server.py

import socket
import sys

from interface import Counter 
import threading

def thread_function(counter, msgClient, socket, addr):
    
        msg_text = msgClient.decode("utf-8")
        print(f"[SERVER] Ricevuto mess dal client {msg_text}")

        tokens = msg_text.split("#")
        
        if(tokens[0] == "setCount"):
            id = tokens[1]
            s = int(tokens[2])
            counter.setCount(id,s)
            print("[SERVER THREAD] Set Count executed")
        elif(tokens[0] == "sum"):
            s = int(tokens[1])
            valore = counter.sum(s)
            print(valore)
            print(f"[SERVER THREAD] Sum executed valore: {valore}")
            msgServer = str(valore).encode("utf-8")
            socket.sendto(msgServer,addr)
        else:
            valore = counter.increment()
            print(valore)
            print(f"[SERVER THREAD] Increment executed valore: {valore}")
            msgServer = str(valore).encode("utf-8")
            socket.sendto(msgServer,addr)


class Skeleton(Counter):
    
    def __init__(self, counter, port):
        self.counter = counter
        self.port = port

    def setCount(self, id, s):
        self.counter.setCount(id,s)
    
    def sum(self, s):
        return self.counter.sum(s)
    
    def increment(self):
        return self.counter.increment()
    
    def run_Skeleton(self):
        IP = 'localhost'
        BUFFER_SIZE = 1024
        
        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.bind((IP.encode("utf-8"), self.port))

        while True:
            msgClient, addr = sock.recvfrom(BUFFER_SIZE)

            t = threading.Thread(target=thread_function, args=(self, msgClient, sock, addr))
            t.start()

        sock.close()
       
            
            
class ServerImpl(Counter):
    
    def __init__(self):
        self.count = 0 

    def setCount(self, id, s):
        self.count = s
        print("[SERVER] Count set by {id} to {s}", id, s)
    
    def sum(self, s):
        self.count += s
        print("[SERVER] Count summed by {s}", s)
        return self.count
    
    def increment(self):
        self.count += 1
        print("[SERVER] Count incremented")
        return self.count


if __name__ == "__main__":
    try:
        PORT = sys.argv[1]
    except IndexError:
        print("specify port")
    
    impl = ServerImpl()
    skeleton = Skeleton(impl, int(PORT))
    skeleton.run_Skeleton()

