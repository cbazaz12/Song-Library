package song;

public class Song {
    String songName;
    String artist;
    String album;
    String year;

    public Song(String s, String ar, String al, String y){
        songName = s;
        artist = ar;
        album = al;
        year = y;
    }

    public Song(String s, String ar){
        songName = s;
        artist = ar;
        album = "";
        year = "";
    }

    public Song(){
        songName = "";
        artist = "";
        album = "";
        year = "";
    }

    public String getSongName(){
        return songName;
    }
    public String getSongNameLower(){
        return songName.toLowerCase();
    }
    public void setSongName(String s){
        songName = s;
    }

    public String getArtist(){
        return artist;
    }
    public void setArtist(String a){
        artist = a;
    }

    public String getAlbum(){
        return album;
    }
    public void setAlbum(String a){
        album = a;
    }

    public String getYear(){
        return year;
    }
    public void setYear(String y){
        year = y;
    }

    public String toString(){
        return songName + " - " + artist;
    }
    
    public String getLongLowerCase(){
        return songName.toLowerCase() + " " + artist.toLowerCase();
    }
}
