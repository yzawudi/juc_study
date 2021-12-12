package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j(topic = "c.Test9")
public class Test9 {
    public static void main(String[] args) throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue(3);
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                //lambda 表达式中使用的变量应为 final 或有效 final
                messageQueue.put(new Message(finalI, "val" + finalI));
            }, "生产者" + i).start();
        }
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = messageQueue.take();
//                    log.debug(message.getValue());
            }
        },"消费者").start();
    }
}

@Slf4j(topic = "c.MessageQueue")
class MessageQueue{
    private LinkedList<Message> linkedList = new LinkedList<>();
    private Integer capcity;

    public MessageQueue(Integer capcity) {
        this.capcity = capcity;
    }
    //获取消息
    public Message take() {
        //检查对象是否为空
        synchronized (linkedList) {
            while (linkedList.isEmpty()) {
                try {
                    log.debug("没有消息");
                    linkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            linkedList.notifyAll();
            log.debug("消费消息");
            return linkedList.removeFirst();
        }
    }
    //存入消息
    public void put(Message message) {
        synchronized (linkedList) {
            while (linkedList.size() == capcity) {
                log.debug("消息队列已满");
                try {
                    linkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("存入消息 {}" , message.getId());
            linkedList.addLast(message);
            linkedList.notifyAll();
        }
    }
}

@Slf4j(topic = "c.Message")
final class Message {
    private Integer id;
    private Object value;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }

    public Message(Integer id, Object value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}