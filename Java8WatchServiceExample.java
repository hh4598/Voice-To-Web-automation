package howtodoinjava;
import static java.nio.file.StandardWatchEventKinds.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;


import example.NewTest;

public class Java8WatchServiceExample {
    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;
    /**
     * Creates a WatchService and registers the given directory
     */
    Java8WatchServiceExample(Path dir) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey, Path>();
 
        walkAndRegisterDirectories(dir);
    }
 
    /**
     * Register the given directory with the WatchService; This function will be called by FileVisitor
     */
    private void registerDirectory(Path dir) throws IOException
    {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE);
        keys.put(key, dir);
    }
 
    /**
     * Register the given directory, and all its sub-directories, with the WatchService.
     */
    private void walkAndRegisterDirectories(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                registerDirectory(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
 
    /**
     * Process all events for keys queued to the watcher
     * @throws InterruptedException 
     * @throws IOException 
     */
    void processEvents() throws InterruptedException, IOException {
        for (;;) {
 
            // wait for key to be signaled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }
 
            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }
 
            for (WatchEvent<?> event : key.pollEvents()) {
                @SuppressWarnings("rawtypes")
                WatchEvent.Kind kind = event.kind();
 
                // Context for directory entry event is the file name of entry
                @SuppressWarnings("unchecked")
                Path name = ((WatchEvent<Path>)event).context();
                Path child = dir.resolve(name);
                String str = name.toString();
                
                // print out event
                System.out.format("%s: %s\n", event.kind().name(), child);
                // if directory is created, and watching recursively, then register it and its sub-directories
                if (kind == ENTRY_CREATE) {
                	System.out.println(str);	
                    if(str.equals("input.txt") ){
//                    	Scanner sc = new Scanner(new File("E:\\Exprmnt\\input.txt"));
//                        String s = null;
//                        Thread.sleep(5000);
//                    	while(sc.hasNext()){
//                            s = sc.nextLine();
//                            System.out.println(s);
//                        }
//                        sc.close();
//                    	BufferedReader reader = new BufferedReader(new FileReader("E:\\Exprmnt\\input.txt"));
//                    	String line = reader.readLine();
//                    	Thread.sleep(3000);
//                    	try {
//                			while (line != null) {
//                				System.out.println(line);
//                				// read next line
//                				line = reader.readLine();
//                			}
//                			reader.close();
//                		} catch (IOException e) {
//                			e.printStackTrace();
//                		}
                    	
                    	String line = null;
                    	try {
                			RandomAccessFile file = new RandomAccessFile("E:\\Exprmnt\\input.txt","r");
                			Thread.sleep(5000);
                			String str1 = null;
                			while ((str1 = file.readLine()) != null) {
                				System.out.println(str1);
                				line = str1;
                			}
                			file.close();
                		} catch (IOException e) {
                			e.printStackTrace();
                		}
                    	NewTest nt = new NewTest();
	                	nt.beforeTest();
	                	nt.testme(line);
	                	nt.afterTest();
                        Thread.sleep(5000);
                        Files.delete(Paths.get("E:\\Exprmnt\\input.txt"));
	                }
                    try {
                        if (Files.isDirectory(child)) {
                            walkAndRegisterDirectories(child);
    
                         }
                    } catch (IOException x) {
                        // do something useful
                    }
                }
            }
 
            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);
 
                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
           }
         }

    public static void main(String[] args) throws IOException, InterruptedException {
        Path dir = Paths.get("E:\\Exprmnt");
        new Java8WatchServiceExample(dir).processEvents();
    }
}