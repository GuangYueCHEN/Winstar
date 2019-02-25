import matplotlib.pyplot as plt

import numpy as np

import csv


csvfile=open("/Users/pingguo/Desktop/winstar/data/commandes/ForEachPersonCount.csv/part-00000-6b41b8cd-b90a-4095-9ffb-10cac6eb3380-c000.csv","r",encoding='utf_8_sig')

reader=csv.reader(csvfile)
 
column1 = [row[1]for row in reader]

column = [ float(i) for i in column1 ]
column.remove(column[0])



plt.hist(column,bins=100,color='green',normed=False)

plt.show()