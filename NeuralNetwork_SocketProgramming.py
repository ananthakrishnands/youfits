import matplotlib.pyplot as plt
import numpy as np
import sklearn
import sklearn.datasets
import sklearn.linear_model
from sklearn.neural_network import MLPClassifier
import matplotlib
import matplotlib.pyplot as plt
import csv
import pandas as pd
from sklearn.model_selection import train_test_split
import threading
from threading import Thread
import pickle
online = {}
def handle_client(conn,user):
    Read(conn)



import socket
def Read(conn):
    dat=pd.read_csv('F:\Computer\PRojects\Python Networking\diabetes2.csv')
    Xnew = dat.iloc[:1492,0:8].values
    ynew= dat.iloc[:1492, 8].values
    a=0.0 
    xt,xtest,yt,ytest=train_test_split(Xnew, ynew, test_size=100)
    from sklearn import preprocessing as pre
    sc = pre.StandardScaler()
    trains = sc.fit_transform(xt)
    tt=sc.fit_transform(xtest)
    #print Xnew[0,:]
    #print xt[0,:]
    #print trains[0,:]
    mp2 = MLPClassifier(activation='relu',solver='lbfgs', alpha=1e-5, hidden_layer_sizes=(10,3), random_state=1,max_iter=2800)
    t_=trains[:,[0,1,2,4,5,7]]
    tt_=tt[:,[0,1,2,4,5,7]]
    mp2.fit(t_,yt)
    # a=mp2.score(tt_,ytest)
    a=mp2.predict(tt_[2:3])
    data_string = str(a)
    conn.send(data_string)
    print ("final score--")
    #mp2.score(tt_,ytest)

def Main():
    host = '127.0.0.1'
    port = 5000

    s = socket.socket()
    s.bind((host,port))

    s.listen(3)
    while 1:
        conn, addr = s.accept()
        print "Connection from: " + str(addr[0]) + " on port" + str(addr[1])
        user = conn.recv(1024)
        print "User: " + user + "  connected to group chat"
        online[user] = conn
        thread = Thread(target=handle_client, args=(conn , user))
        thread.start()

if __name__ == '__main__':
    Main()
