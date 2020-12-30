package cn.structure.example.mybatis.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;
import lombok.Data;

@Data
public class User implements Serializable {
    private Long id;

    private String username;

    private String password;

    private Boolean isUnexpired;

    private Boolean isEnabled;

    private Boolean isUnlocked;

    private Boolean isDeleted;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    private String updateSql;

    public String getUpdateSql() {
        return this.updateSql;
    }

    public void setUpdateSql(String updateSql) {
        this.updateSql = updateSql;
    }

    public String toJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTimeZone(TimeZone.getDefault());
        return mapper.writeValueAsString(this);
    }
}