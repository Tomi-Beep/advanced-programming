package exam_problems.p03_stringbuilder;

import java.util.*;

class FileNameExistsException extends Exception {
    public FileNameExistsException(String file, String folder) {
        super("There is already a file named " + file + " in the folder " + folder);
    }
}

interface IFile {
    String getFileName();

    long getFileSize();

    void sortBySize();

    long findLargestFile();
}

class File implements IFile {
    private final String name;
    private final long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public void sortBySize() {}

    @Override
    public long findLargestFile() {
        return size;
    }

    @Override
    public String toString() {
        return String.format("File name: %10s File size: %10d", name, size);
    }
}

class Folder implements IFile {
    private final String name;
    //    private long size;
    private List<IFile> files;

    public Folder(String name) {
        this.name = name;
        files = new ArrayList<>();
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return files.stream()
                .mapToLong(IFile::getFileSize)
                .sum();
    }

    public List<IFile> getFiles() {
        return files;
    }

    @Override
    public void sortBySize() {
        files.forEach(IFile::sortBySize);
        files.sort(Comparator.comparingLong(IFile::getFileSize));
    }

    @Override
    public long findLargestFile() {
        return files.stream()
                .mapToLong(IFile::findLargestFile)
                .max()
                .orElse(0L);
    }

    void addFile(IFile file) throws FileNameExistsException {
        if (files.stream().anyMatch(f -> f.getFileName().equals(file.getFileName())))
            throw (new FileNameExistsException(file.getFileName(), getFileName()));
        files.add(file);
//        for (IFile f : files){
//            if (f.getFileName().equals(file.getFileName()))
//                throw (new FileNameExistsException(file.getFileName(), getFileName()));
//
//        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Folder name: %10s Folder size: %10d", name, getFileSize()));

        for (IFile file : files) {
            String childOutput = file.toString();
            String[] lines = childOutput.split("\n");
            for (String line : lines) {
                sb.append("\n    ").append(line);
            }
        }
        sb.append("\n");
        return sb.toString();
    }
}

class FileSystem {
    Folder rootDirectory;

    public FileSystem() {
        rootDirectory = new Folder("root");
    }

    void addFile(IFile file) throws FileNameExistsException {
        rootDirectory.addFile(file);
    }

    long findLargestFile() {
        return rootDirectory.findLargestFile();
    }

    void sortBySize() {
        rootDirectory.sortBySize();
//        rootDirectory.getFiles().sort(Comparator.comparingLong(IFile::getFileSize));
    }

    @Override
    public String toString() {
        return rootDirectory.toString();
    }
}

public class FileSystemTest {

    public static Folder readFolder(Scanner sc) {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < totalFiles; i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String[] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args) {

        //file reading from input

        Scanner sc = new Scanner(System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());


    }
}