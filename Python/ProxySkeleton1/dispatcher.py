from interface import Dispatcher
import socket
import threading

def thread_func(self,conn:socket.socket):
    data = conn.recv(1024)
    
    tokens = data.decode("utf-8").split("#")
    if(tokens[0] == "sendCmd"):
        print("[SERVER] Arrived send request")
        cmd = tokens[1]
        self.sendCmd(cmd)
        conn.close()
    elif(tokens[0] == "getCmd"):
        print("[SERVER] Arrived get request")
        cmd = self.getCmd()
        conn.send(str(cmd).encode("utf=8"))
        print(f"[SERVER] Sent command {cmd} to actuator")
        conn.close()
    else:
        print("[SERVER] Method not recognised")

class Skeleton(Dispatcher):

    def __init__(self, port:int):
        self.port = port

    def run_Skeleton(self):
        IP = 'localhost'

        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.bind((IP, self.port))

        sock.listen(5)

        while True:
            conn, addr = sock.accept()    
            t = threading.Thread(target=thread_func, args=(self,conn)) 
            t.start()

        sock.close()


class DispatcherImpl(Skeleton):

    def __init__(self,port):
        Skeleton.__init__(self, port)
        self.lock = threading.Lock()
        self.spazio_disp_cv = threading.Condition(self.lock)
        self.risorse_disp_cv = threading.Condition(self.lock)
        self.queue = []
        self.size = 5

    def empty(self):
        return len(self.queue) == 0 
    
    def full(self):
        return len(self.queue) == self.size

    def sendCmd(self, command: int):

        with self.spazio_disp_cv:

            while self.full():
                self.spazio_disp_cv.wait()

            self.queue.insert(0,command)

            self.risorse_disp_cv.notify()

    def getCmd(self):
        
        command = 0 

        with self.risorse_disp_cv:

            while self.empty():
                self.risorse_disp_cv.wait()

            command = self.queue.pop()


            self.spazio_disp_cv.notify()
            return command


if __name__ == "__main__":
    impl = DispatcherImpl(8000)
    impl.run_Skeleton()


