import matplotlib.pyplot as plt

import numpy as np

import csv

csvfile=open("/Users/pingguo/Desktop/winstar/data/commandes/ForEachAge.csv/part-00000-1c1a94b7-d068-44b0-b891-ec786efaf9cf-c000.csv","r",encoding='utf_8_sig')

reader=csv.reader(csvfile)
    
column0 = [row[0]for row in reader]

csvfile=open("/Users/pingguo/Desktop/winstar/data/commandes/ForEachAge.csv/part-00000-1c1a94b7-d068-44b0-b891-ec786efaf9cf-c000.csv","r",encoding='utf_8_sig')

reader=csv.reader(csvfile)
 
column1 = [row[1]for row in reader]

column = [ int(i) for i in column1 ]
print(column0)
print(column1)

plt.bar(left=column0,height=column,color='green',width=0.5)
 

plt.show()