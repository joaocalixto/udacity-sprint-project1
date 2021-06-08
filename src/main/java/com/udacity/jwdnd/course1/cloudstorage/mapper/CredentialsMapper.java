package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.data.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.data.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credentials credentials);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credentials> getCredentialsFromUser(Integer userid);

    @Select("SELECT * FROM CREDENTIALS")
    List<Notes> listAll();

    @Delete("delete from CREDENTIALS where credentialid =#{credentialid}")
    int delete(Integer credentialid);

    @Update("UPDATE CREDENTIALS set " +
            "url=#{url}, " +
            "username=#{username}, " +
            "key=#{key}, "+
            "password=#{password} " +
            "WHERE credentialid = #{credentialid}")
    int update(Credentials credential);
}
