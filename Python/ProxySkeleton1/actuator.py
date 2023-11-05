import socket
import sys
from time import sleep
from interface import Dispatcher


class Proxy(Dispatcher):
    def __init__(self, port:int):
        self.port = port 
    
    def getCmd(self):
        IP = 'localhost'
        
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.connect((IP, self.port))

        msg = "getCmd#"
        sock.send(msg.encode("utf-8"))
        print("[ACTUATOR] Sent get request")

        msg_Server = sock.recv(1024)
        sock.close()
        msg_text = msg_Server.decode("utf-8")
        print(f"[ACTUATOR] Get value: {msg_text}")
        return int(msg_text)



if __name__ == "__main__":
    try:
        PORT = sys.argv[1]
    except IndexError:
        print("IL portoooo")

    proxy_actuator = Proxy(int(PORT))

    while True:
        sleep(1)
        cmd = proxy_actuator.getCmd()

        with open("cmdlog.txt", mode="a") as file:
            file.write(str(cmd))
            file.close()
