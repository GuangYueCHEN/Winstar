import matplotlib.pyplot as plt

import numpy as np

import csv

import matplotlib as mpl
csvfile=open("/Users/pingguo/Desktop/winstar/data/commandes/ForEachActivityCount.csv/part-00000-510ae79c-9068-43e5-a76b-499677d01f14-c000.csv","r",encoding='utf_8_sig')

reader=csv.reader(csvfile)
    
column0 = [row[0]for row in reader]

names=[]
for id in column0:
    csvfile=open("/Users/pingguo/Desktop/winstar/data/commandes/cbc_activity.csv","r",encoding='utf_8_sig')
    
    name=csv.reader(csvfile)
    
    for row in name:

        if id == row[0]  :
            id = row[1]
            names.append(id)
            break


csvfile=open("/Users/pingguo/Desktop/winstar/data/commandes/ForEachActivityCount.csv/part-00000-510ae79c-9068-43e5-a76b-499677d01f14-c000.csv","r",encoding='utf_8_sig')

reader=csv.reader(csvfile)
 
column1 = [row[1]for row in reader]

column = [ int(i) for i in column1 ]

sum=0
for i in range(1,5):
    sum+=column.pop()
    names.pop()
column.append(sum)
names.append('Autre')
print(column)
print(names)

plt.axes(aspect=1)#使x y轴比例相同

Explode=0.05,0.05,0.05,0.05,0.05,0.05,0.05
pie = plt.pie(x=column,labels=names,autopct='%.0f%%',explode=Explode)#autopct显示百分比

for font in pie[1]:
    
    font.set_fontproperties(mpl.font_manager.FontProperties(
            fname='/Library/Fonts/Songti.ttc'))



plt.show()