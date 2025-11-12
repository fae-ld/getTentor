package com.atomic.getTentor.dto;


public record MailBody(String to, String subject, String text) {
    public static class Builder {
        private String to;
        private String subject;
        private String text;

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public MailBody build() {
            return new MailBody(to, subject, text);
        }
    }
}

