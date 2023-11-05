import logging
from random import randint
import threading
import time

QUEUE_SIZE = 5 

logging.basicConfig(level=logging.DEBUG, format='[%(threadName)-0s] %(message)s')


def space_available(queue):
    return (len(queue) == 0)



def item_available(queue):
    return (len(queue) == QUEUE_SIZE)

def get_item(queue): 
   return queue.pop()


def add_item(queue):
    item = randint(0,100) 
    queue.insert(0,item)


class consumerThread(threading.Thread):

    def __init__(self, consumer_cv, producer_cv, queue, name):
        threading.Thread.__init__(self, name=name)
        self.consumer_cv = consumer_cv
        self.producer_cv = producer_cv 
        self.queue = queue 


    def run(self):
        logging.debug("Start")


        with self.consumer_cv:

            logging.debug("Lock acquired")

            while not item_available(self.queue):
                logging.debug("Waiting for condition")  
                self.consumer_cv.wait() 


            time.sleep(1.0)
            item = get_item(self.queue)
            logging.debug('Obtained Item: %r', item)

            self.producer_cv.notify() 

        logging.debug("Released lock")

    

class producerThread(threading.Thread):

    def __init__(self, consumer_cv, producer_cv, queue, name):
        threading.Thread.__init__(self, name=name)
        self.consumer_cv = consumer_cv
        self.producer_cv = producer_cv 
        self.queue = queue 


    def run(self):
        logging.debug("Start")


        with self.producer_cv:

            logging.debug("Lock acquired")

            while not space_available(self.queue):
                logging.debug("Waiting for condition")  
                self.producer_cv.wait() 


            time.sleep(1.0)
            add_item(self.queue)
            logging.debug("Added Item")

            self.consumer_cv.notify() 

        logging.debug("Released lock")


def main():

    queue = []
    cv_lock = threading.Lock()
    producer_cv = threading.Condition(lock=cv_lock)
    consumer_cv = threading.Condition(lock=cv_lock)

    consumers = []
    producers = []

    for i in range(10):
        name = "CONSUMER " + str(i) 

        ct = consumerThread(consumer_cv, producer_cv, queue, name) 
        ct.start()
        consumers.append(ct)

    for i in range(10):
        name = "PRODUCER " + str(i) 

        pt = producerThread(consumer_cv, producer_cv, queue, name) 
        pt.start()
        producers.append(pt)


    for i in range(10):
        consumers[i].join()

    for i in range(10):
        producers[i].join()



if __name__ == "__main__":
    main()