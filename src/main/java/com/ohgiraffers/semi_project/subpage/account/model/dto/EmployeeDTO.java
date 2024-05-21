package com.ohgiraffers.semi_project.subpage.account.model.dto;

public class EmployeeDTO {

        private int user_no;
        private String user_name;
        private int user_id;

        public EmployeeDTO() {
        }

        public EmployeeDTO(int user_no, String user_name, int user_id) {
            this.user_no = user_no;
            this.user_name = user_name;
            this.user_id = user_id;
        }

        public int getUser_no() {
            return user_no;
        }

        public void setUser_no(int user_no) {
            this.user_no = user_no;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        @Override
        public String toString() {
            return "EmployeeDTO{" +
                    "user_no=" + user_no +
                    ", user_name='" + user_name + '\'' +
                    ", user_id=" + user_id +
                    '}';
        }
    }

