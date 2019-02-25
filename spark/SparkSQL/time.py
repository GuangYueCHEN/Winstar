import matplotlib.pyplot as plt

import numpy as np

import csv

csvfile=open("/Users/pingguo/Desktop/winstar/data/commandes/ForEachTime.csv/part-00000-674e95c7-9a28-4283-873a-ec3ff0200289-c000.csv","r",encoding='utf_8_sig')

reader=csv.reader(csvfile)
    
column0 = [row[0]for row in reader]

csvfile=open("/Users/pingguo/Desktop/winstar/data/commandes/ForEachTime.csv/part-00000-674e95c7-9a28-4283-873a-ec3ff0200289-c000.csv","r",encoding='utf_8_sig')

reader=csv.reader(csvfile)
 
column1 = [row[1]for row in reader]

column = [ int(i) for i in column1 ]
print(column0)
print(column1)


plt.plot(column0,column,linestyle='--',color='green',marker='.')

plt.show()