package org.goriachev.homework.models;

import androidx.annotation.NonNull;

public class Message implements Cloneable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // отправитель
    private String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    // получатель
    private String receiver;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    // признак вложения
    private boolean isAttach;

    public boolean isAttach() {
        return isAttach;
    }

    public void setAttach(boolean attach) {
        isAttach = attach;
    }

    // тема сообщение
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // текст сообщения
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Message() {
    }

    public Message(int id, String sender, String receiver, boolean isAttach, String subject, String text) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.isAttach = isAttach;
        this.subject = subject;
        this.text = text;
    }

    @NonNull
    @Override
    public Message clone() {
        try {
            return (Message) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
