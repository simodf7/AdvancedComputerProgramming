from time import sleep
import stomp
import random


class ClientListener(stomp.ConnectionListener):

    def on_message(self, frame):
        print(f"Received message from dispatcher: {frame.body}"); 






if __name__ == "__main__":
    conn = stomp.Connection([('127.0.0.1', 61613)], auto_content_length=False) 
    conn.set_listener(" ", ClientListener())
    conn.connect(wait=True)
    conn.subscribe(destination="/queue/response", id=1, ack="auto")
    
    for i in range(10):

        if (i%2 == 0):
            request = "deposita"
            id = random.randint(1,100)
            MSG = request + "-" + str(id)
        else:
            MSG = "preleva"

        conn.send("/queue/request", MSG)

    while True:
        sleep(60)
        
    conn.disconnect()





