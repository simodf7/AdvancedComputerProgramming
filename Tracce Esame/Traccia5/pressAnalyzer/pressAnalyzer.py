import csv
import time
import stomp
from matplotlib import pyplot as plt
import numpy as np

class PressAnalyzer(stomp.ConnectionListener):

    def __init__(self,conn):
        self.index = 1
        self.conn = conn

    def on_message(self, frame):
        value = int(frame.body)
        print(f"Valore: {value}")
        with open('press.csv', mode='a', newline='') as values:
            writer = csv.writer(values)
            writer.writerow([self.index,value])
            self.index += 1







if __name__ == "__main__":
    conn = stomp.Connection([('127.0.0.1', 61613)],auto_content_length=False)
    listener = PressAnalyzer(conn)
    conn.set_listener('', listener)
    conn.connect(wait=True)
    conn.subscribe("/topic/press", id=1, ack='auto')
    
    
    
    time.sleep(120)
    conn.disconnect()
