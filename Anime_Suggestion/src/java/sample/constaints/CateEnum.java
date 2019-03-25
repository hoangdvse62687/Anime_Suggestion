/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.constaints;

/**
 *
 * @author DELL
 */
public enum CateEnum {
    SLICE_OF_LIFE("Đời Thường"),
    ACTION("Hành Động"),
    SHOUNEN("Shounen"),
    HAREM("Harem"),
    SCHOOL("Học Đường"),
    SPORTS("Thể Thao"),
    DRAMA("Drama"),
    DETECTIVE("Trinh Thám"),
    HORROR("Kinh Dị"),
    MECHA("Mecha"),
    MAGIC("Phép Thuật"),
    ADVENTURE("Phiêu Lưu"),
    ECCHI("Ecchi"),
    COMEDY("Hài Hước"),
    ROMANCE("Romance"),
    HISTORICAL("Lịch Sử"),
    MUSIC("Âm Nhạc"),
    SCI_FI("Viễn Tưởng"),
    FANTASY("Fantasy"),
    GAME("Game"),
    SHOUJO("Shoujo"),
    SEINEN("Seinen"),
    SUPER_POWER("Siêu Năng Lực"),
    SUPER_NATURAL("Siêu Nhiên"),
    MYSTERY("Mystery"),
    PSYCHOLOGICAL("Psychological"),
    MILITARY("Military"),
    THRILLER("Thriller");
    private final String value;
    
    CateEnum(String v){
        value = v;
    }
    
    public String getValue(){
        return value;
    }
}
