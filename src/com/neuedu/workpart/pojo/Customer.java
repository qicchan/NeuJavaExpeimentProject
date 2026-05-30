package com.neuedu.workpart.pojo;

public class Customer {
    private Integer id;// 主键ID
    private Integer is_deleted;// 是否删除(0未删除/1已删除)
    private String customer_name;// 客户姓名
    private Integer customer_age;// 客户年龄(由身份证号计算得出)
    private Integer customer_sex;// 客户性别(0男/1女)
    private String idcard;// 身份证号
    private String room_no;// 房间号
    private String building_no;// 楼栋
    private String checkin_date;// 入住时间
    private String expiration_date;// 合同到期时间
    private String contact_tel;// 联系电话
    private Integer bed_id;// 床位ID
    private String psychosomatic_state;// 身心状态
    private String attention;// 注意事项
    private String birthday;// 生日
    private String height;// 身高
    private String weight;// 体重
    private String blood_type;// 血型
    private String filepath;// 文件路径
    private Integer user_id;// 用户ID
    private Integer level_id;// 护理等级ID
    private String family_member;// 家属

    public Customer() {
    }

    /**
     * @param customer_name   客户姓名
     * @param customer_age    客户年龄
     * @param customer_sex    客户性别(0男/1女)
     * @param idcard          身份证号
     * @param blood_type      血型
     * @param family_member   家属
     * @param contact_tel     联系电话
     * @param building_no     楼栋
     * @param room_no         房间号
     * @param checkin_date    入住时间
     * @param expiration_date 合同到期时间
     */
    public Customer(String customer_name, Integer customer_age, Integer customer_sex, String idcard,
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
        if (idcard != null && idcard.length() >= 14) {
            this.customer_age = java.time.LocalDate.now().getYear() - Integer.parseInt(idcard.substring(6, 10));
        } else {
            this.customer_age = customer_age;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public Integer getCustomer_age() {
        return customer_age;
    }

    public void setCustomer_age(Integer customer_age) {
        this.customer_age = customer_age;
    }

    public Integer getCustomer_sex() {
        return customer_sex;
    }

    public void setCustomer_sex(Integer customer_sex) {
        this.customer_sex = customer_sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
        if (idcard != null && idcard.length() >= 14) {
            this.customer_age = java.time.LocalDate.now().getYear() - Integer.parseInt(idcard.substring(6, 10));
        } else {
            this.customer_age = customer_age;
        }
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getBuilding_no() {
        return building_no;
    }

    public void setBuilding_no(String building_no) {
        this.building_no = building_no;
    }

    public String getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(String checkin_date) {
        this.checkin_date = checkin_date;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getContact_tel() {
        return contact_tel;
    }

    public void setContact_tel(String contact_tel) {
        this.contact_tel = contact_tel;
    }

    public Integer getBed_id() {
        return bed_id;
    }

    public void setBed_id(Integer bed_id) {
        this.bed_id = bed_id;
    }

    public String getPsychosomatic_state() {
        return psychosomatic_state;
    }

    public void setPsychosomatic_state(String psychosomatic_state) {
        this.psychosomatic_state = psychosomatic_state;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getLevel_id() {
        return level_id;
    }

    public void setLevel_id(Integer level_id) {
        this.level_id = level_id;
    }

    public String getFamily_member() {
        return family_member;
    }

    public void setFamily_member(String family_member) {
        this.family_member = family_member;
    }

    /**
     * 重写toString，输出含参构造对应的字段信息
     * 包含：客户姓名、年龄、性别、身份证号、血型、家属、联系电话、楼栋、房间号、入住时间、合同到期时间
     */
    @Override
    public String toString() {
        return "Customer{" +
                "customer_name='" + customer_name + '\'' + // 客户姓名
                ", customer_age=" + customer_age + // 客户年龄
                ", customer_sex=" + customer_sex + // 客户性别(0男/1女)
                ", idcard='" + idcard + '\'' + // 身份证号
                ", blood_type='" + blood_type + '\'' + // 血型
                ", family_member='" + family_member + '\'' + // 家属
                ", contact_tel='" + contact_tel + '\'' + // 联系电话
                ", building_no='" + building_no + '\'' + // 楼栋
                ", room_no='" + room_no + '\'' + // 房间号
                ", checkin_date='" + checkin_date + '\'' + // 入住时间
                ", expiration_date='" + expiration_date + '\'' + // 合同到期时间
                '}';
    }
}