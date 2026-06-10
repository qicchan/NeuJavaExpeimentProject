package com.neuedu.workpart.pojo;

public class ResidentCustomer {
    private Integer id;
    private Integer is_deleted;
    private String customer_name;
    private Integer customer_age;
    private Integer customer_sex;
    private String idcard;
    private String room_no;
    private String building_no;
    private String checkin_date;
    private String expiration_date;
    private String contact_tel;
    private Integer bed_id;
    private String psychosomatic_state;
    private String attention;
    private String birthday;
    private String height;
    private String weight;
    private String blood_type;
    private String filepath;
    private Integer user_id;
    private Integer level_id;
    private String family_member;
    private String careLevel;

    public ResidentCustomer() {
    }

    public ResidentCustomer(String customer_name, Integer customer_age, Integer customer_sex, String idcard,
                            String blood_type, String family_member, String contact_tel, String building_no,
                            String room_no, String checkin_date, String expiration_date) {
        this.customer_name = customer_name;
        this.customer_sex = customer_sex;
        this.idcard = idcard;
        this.blood_type = blood_type;
        this.family_member = family_member;
        this.contact_tel = contact_tel;
        this.building_no = building_no;
        this.room_no = room_no;
        this.checkin_date = checkin_date;
        this.expiration_date = expiration_date;
        if (idcard != null && idcard.length() >= 18) {
            java.time.LocalDate birthDate = java.time.LocalDate.parse(idcard.substring(6, 14),
                    java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
            this.customer_age = java.time.Period.between(birthDate, java.time.LocalDate.now()).getYears();
        } else {
            this.customer_age = customer_age;
        }
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getIs_deleted() { return is_deleted; }
    public void setIs_deleted(Integer is_deleted) { this.is_deleted = is_deleted; }
    public String getCustomer_name() { return customer_name; }
    public void setCustomer_name(String customer_name) { this.customer_name = customer_name; }
    public Integer getCustomer_age() { return customer_age; }
    public void setCustomer_age(Integer customer_age) { this.customer_age = customer_age; }
    public Integer getCustomer_sex() { return customer_sex; }
    public void setCustomer_sex(Integer customer_sex) { this.customer_sex = customer_sex; }
    public String getIdcard() { return idcard; }
    public void setIdcard(String idcard) {
        this.idcard = idcard;
        if (idcard != null && idcard.length() >= 18) {
            java.time.LocalDate birthDate = java.time.LocalDate.parse(idcard.substring(6, 14),
                    java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
            this.customer_age = java.time.Period.between(birthDate, java.time.LocalDate.now()).getYears();
        }
    }
    public String getRoom_no() { return room_no; }
    public void setRoom_no(String room_no) { this.room_no = room_no; }
    public String getBuilding_no() { return building_no; }
    public void setBuilding_no(String building_no) { this.building_no = building_no; }
    public String getCheckin_date() { return checkin_date; }
    public void setCheckin_date(String checkin_date) { this.checkin_date = checkin_date; }
    public String getExpiration_date() { return expiration_date; }
    public void setExpiration_date(String expiration_date) { this.expiration_date = expiration_date; }
    public String getContact_tel() { return contact_tel; }
    public void setContact_tel(String contact_tel) { this.contact_tel = contact_tel; }
    public Integer getBed_id() { return bed_id; }
    public void setBed_id(Integer bed_id) { this.bed_id = bed_id; }
    public String getPsychosomatic_state() { return psychosomatic_state; }
    public void setPsychosomatic_state(String psychosomatic_state) { this.psychosomatic_state = psychosomatic_state; }
    public String getAttention() { return attention; }
    public void setAttention(String attention) { this.attention = attention; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getHeight() { return height; }
    public void setHeight(String height) { this.height = height; }
    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }
    public String getBlood_type() { return blood_type; }
    public void setBlood_type(String blood_type) { this.blood_type = blood_type; }
    public String getFilepath() { return filepath; }
    public void setFilepath(String filepath) { this.filepath = filepath; }
    public Integer getUser_id() { return user_id; }
    public void setUser_id(Integer user_id) { this.user_id = user_id; }
    public Integer getLevel_id() { return level_id; }
    public void setLevel_id(Integer level_id) { this.level_id = level_id; }
    public String getFamily_member() { return family_member; }
    public void setFamily_member(String family_member) { this.family_member = family_member; }
    public String getCareLevel() { return careLevel; }
    public void setCareLevel(String careLevel) { this.careLevel = careLevel; }

    @Override
    public String toString() {
        return "\n============客户信息=============" +
                "\n客户姓名:" + customer_name +
                "\n客户年龄:" + customer_age +
                "\n客户性别:" + (customer_sex == 0 ? "男" : "女") +
                "\n身份证号:" + idcard +
                "\n血型:" + blood_type +
                "\n家属:" + family_member +
                "\n联系电话:" + contact_tel +
                "\n楼栋:" + building_no +
                "\n房间号:" + room_no +
                "\n所属护工id:" + user_id +
                "\n护理等级:" + careLevel +
                "\n入住时间:" + checkin_date +
                "\n合同到期时间:" + expiration_date +
                "\n==========================";
    }
}
