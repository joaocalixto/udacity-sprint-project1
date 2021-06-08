package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.data.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Insert("INSERT INTO NOTES (userid, notetitle, notedescription) " +
            "VALUES(#{userid}, #{notetitle}, #{notedescription})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Notes note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Notes> getNotesFromUser(Integer userid);

    @Select("SELECT * FROM NOTES")
    List<Notes> listAll();

    @Delete("delete from NOTES where noteid =#{noteid}")
    int delete(Integer noteid);

    @Update("UPDATE NOTES set notetitle=#{notetitle}, notedescription=#{notedescription} WHERE noteid = #{noteid}")
    int update(Notes note);
}
