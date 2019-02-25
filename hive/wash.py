
import csv

myReader=csv.reader(open('/Users/pingguo/Desktop/car_place.csv',encoding='utf-8'))
#i=0
with open('/Users/pingguo/Desktop/car_place_clear.csv','w',newline='',encoding='utf-8') as csvfile:
    myWriter=csv.writer(csvfile)
    for row in myReader:
    #    i=i+1
     #   if i==100:
       #     break
        if row[0][14]=='无' or row[0][14]==' 'or row[0][14]=='0'or row[0][14]=='不'or row[0][14]=='*' or row[0][3]=='c'or row[0][16]=='\x01':
          #  i=i-1
            continue
        else:
            myWriter.writerow(row)
         
        
    