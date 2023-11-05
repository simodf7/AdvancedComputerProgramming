from time import sleep
from interface import Dispatcher
import socket, sys
import random
import threading



class Proxy(Dispatcher):

    def __init__(self, port:int):
        self.port = port 
    
    def sendCmd(self, command: int):
        IP = 'localhost'
        
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.connect((IP, self.port))

        msg = "sendCmd#" + str(command)
        sock.send(msg.encode("utf-8"))
        print(f"[CLIENT] Sent send request with command {command}")
        sock.close()

def thread_function(proxy_client:Proxy):
    for i in range(3):
        sleep(random.randint(2,4))
        proxy_client.sendCmd(random.randint(0,3))
   



if __name__ == "__main__":
    try:
        PORT = sys.argv[1]
    except IndexError:
        print("specify port")

    proxy_client = Proxy(int(PORT))

    for i in range(5):
        t = threading.Thread(target=thread_function, args=(proxy_client,))
        t.start()
        print("[CLIENT] Thread creato")

