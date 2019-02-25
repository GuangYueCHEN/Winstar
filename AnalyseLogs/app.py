import json
from flask import Flask,render_template
from flask_socketio import SocketIO
from kafka import KafkaConsumer

app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret!'
socketio = SocketIO(app)
thread = None

consumer=KafkaConsumer('result',group_id='1',bootstrap_servers=['192.168.56.101:9092'])

def backgroud_thread():
    men=0
    women=0
    for msg in consumer:
        data_json = msg.value.decode('utf8')
        data_list = json.loads(data_json)
        
        for data in data_list:
            if '0' in data.keys():
                women = data['0']
            elif '1' in data.keys():
                men = data['1']
            else:
                continue
        result=str(women)+','+str(men)
        print(result)        
        socketio.emit('numbers_of_people',{'data':result})



@socketio.on('client_connect')
def connect(message):
    print(message)
    global thread
    if thread is None:
        thread = socketio.start_background_task(target=backgroud_thread)
    
    socketio.emit('connected',{'data':'Connected'})
    
@app.route("/")
def handle_message():
    return render_template("index.html")

if __name__ == '__main__':
    socketio.run(app,debug=True)