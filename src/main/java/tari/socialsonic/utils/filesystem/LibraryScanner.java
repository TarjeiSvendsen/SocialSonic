package tari.socialsonic.utils.filesystem;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;

import java.io.File;
import java.util.Stack;

public class LibraryScanner {

    public LibraryScanner(){

    }

    public int scanRoot(){
        Stack<File> files = new Stack<>();
        File libraryRoot = new File("src/main/resources/testMusicLibrary");
        for (File file: libraryRoot.listFiles()){
            if (file.isDirectory()){
                scanDirectory(files,file);
            }
            else files.add(file);
        }
        while(!files.isEmpty()){
            File file = files.pop();
            try{
                AudioFile f = AudioFileIO.read(file);
                Tag tag = f.getTag();
                // TODO, things
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return 0;
    }

    public boolean scanDirectory(Stack<File> files, File directory){
        for (File file: directory.listFiles()){
            if (file.isDirectory()){
                scanDirectory(files,file);
            }
            else files.add(file);
        }
        return true;
    }
}
