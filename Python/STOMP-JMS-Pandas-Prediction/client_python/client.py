import time
import stomp 
import random 
import pandas as pd
from matplotlib import pyplot as plt
import numpy as np 



class MyListener(stomp.ConnectionListener):

    def __init__(self):
        self.predictions = []

    def on_message(self, frame):
        print(f"Received message: {frame.body} ")

        if "prediction" in frame.body:
            prediction = (frame.body).split("-")[1]
            self.predictions.append(prediction)
            print(len(self.predictions))

    def get_predictions(self):
        return self.predictions


    
if __name__ == "__main__":
    conn = stomp.Connection([('127.0.0.1', 61613)], auto_content_length=False)
    listener = MyListener()
    conn.set_listener('', listener)
    conn.connect(wait = True)
    conn.subscribe('/queue/response', id=1, ack="auto")


    years = []
    forecast_reqs = 30 
    for i in range(forecast_reqs):

        request = "forecast"
        
        year = random.randint(2021,2121)
        years.append(year)
        MSG = request + "-" + str(year)

        conn.send("/queue/request", MSG)
        print(f"Sent request {i}: {MSG} ")
    
    while True:
        time.sleep(10)
        #print(listener.get_predictions())

        if (len(listener.get_predictions()) == forecast_reqs):

            nyc = pd.read_csv("USH00305801-tmax-1-1-1895-2022.csv", skiprows=4)
            nyc.columns = ['Date', 'Temperature', 'Anomaly']
            nyc.Date = nyc.Date.floordiv(100)

            fig, ax = plt.subplots()

            new_dates = pd.Series(years)
            new_temps = pd.Series(listener.get_predictions(), dtype="float")

            nan_series = pd.Series(np.nan, index=range(0, len(years)))

            newDates = pd.concat([nan_series, new_dates])
            newTemps = pd.concat([nan_series, new_temps])

            ax.scatter(nyc.Date, nyc.Temperature)
            ax.scatter(newDates, newTemps, alpha=0.25)

            ax.legend(['Original', 'Predicted'])
            ax.set_xlabel('Years')
            ax.set_ylabel('Temperature')

            plt.savefig('predictions.png')
            plt.show()
            break
            
        conn.disconnect()





