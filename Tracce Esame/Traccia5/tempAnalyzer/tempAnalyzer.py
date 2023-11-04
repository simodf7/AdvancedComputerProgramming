import time
import stomp
from matplotlib import pyplot as plt
import numpy as np

class TempAnalyzer(stomp.ConnectionListener):

    def __init__(self,conn):
        self.queue = []
        self.conn = conn

    def on_message(self, frame):
        value = int(frame.body)
        print(f"Valore: {value}")
        self.queue.append(value)

        if(len(self.queue) == 20):
            fig = plt.figure()
            ax = plt.axes()
            ax.grid()
            x = np.linspace(1,20, 20)
            ax.set_title("Temperatura")
            ax.set_xlabel("Campione")
            ax.set_ylabel("Temperatura")
            ax.plot(x, self.queue)
            plt.show()






if __name__ == "__main__":
    conn = stomp.Connection([('127.0.0.1', 61613)],auto_content_length=False)
    listener = TempAnalyzer(conn)
    conn.set_listener('', listener)
    conn.connect(wait=True)
    conn.subscribe("/topic/temp", id=1, ack='auto')
    
    
    
    time.sleep(120)
    conn.disconnect()
