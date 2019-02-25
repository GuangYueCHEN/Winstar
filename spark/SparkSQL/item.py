import matplotlib.pyplot as plt

import numpy as np

import csv

import matplotlib as mpl
def clear(list):
    res=[]
    for str in list:
        
        str = str[0:str.index('【')]
        res.append(str)
    return res
 
def combine(list1,list2):
    kv=[]
        
    list = []
    for id in list1:
        if id not in list:
            list.append(id)
    res1=[]
    res2=[]
    for key in list:
        sum=0
        for i in range(0,len(list1)):
            if list1[i] == key:
                sum+=list2[i]
        
        res1.append(key)
        res2.append(sum)
    
    
    return res1,res2
    
       
csvfile=open("/Users/pingguo/Desktop/winstar/data/commandes/ForEachItemCount.csv/part-00000-aef71401-41f4-4195-9005-7f60bbd5834f-c000.csv","r",encoding='utf_8_sig')

reader=csv.reader(csvfile)
    
column0 = [row[0]for row in reader]

names=[]
for id in column0:
    csvfile=open("/Users/pingguo/Desktop/winstar/data/commandes/cbc_goods.csv","r",encoding='utf_8_sig')
    
    name=csv.reader(csvfile)
    
    for row in name:

        if id == row[0]  :
            id = row[1]
            names.append(id)
            break

            
names=clear(names)



csvfile=open("/Users/pingguo/Desktop/winstar/data/commandes/ForEachItemCount.csv/part-00000-aef71401-41f4-4195-9005-7f60bbd5834f-c000.csv","r",encoding='utf_8_sig')

reader=csv.reader(csvfile)

 
column1 = [row[1]for row in reader]

column = [ int(i) for i in column1 ]

res1,res2=combine(names,column)

plt.axes(aspect=1)#使x y轴比例相同
 
Explode=0.01,0.01,0.01,0.01,0.01,0.01,0.1,0.5,0.9
 
pie = plt.pie(x=res2,labels=res1,autopct='%.0f%%',explode=Explode)#autopct显示百分比

for font in pie[1]:
    
    font.set_fontproperties(mpl.font_manager.FontProperties(
            fname='/Library/Fonts/Songti.ttc'))



plt.show()