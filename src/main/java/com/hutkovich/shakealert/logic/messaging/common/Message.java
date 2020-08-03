package com.hutkovich.shakealert.logic.messaging.common;

public class Message<H, B> {
  private H header;
  private B body;

  public Message() {
  }

  public Message(H header, B body) {
    this.header = header;
    this.body = body;
  }

  public H getHeader() {
    return header;
  }

  public void setHeader(H header) {
    this.header = header;
  }

  public B getBody() {
    return body;
  }

  public void setBody(B body) {
    this.body = body;
  }
}
