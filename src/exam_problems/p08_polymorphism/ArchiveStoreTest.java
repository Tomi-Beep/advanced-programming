package exam_problems.p08_polymorphism;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

class NonExistingItemException extends Exception {
    public NonExistingItemException(int id) {
        super(String.format("Item with id %d doesn't exist", id));
    }
}

class ArchiveStore {
    List<Archive> archives;
    StringBuilder log;

    public ArchiveStore() {
        archives = new ArrayList<>();
        log = new StringBuilder();
    }

    void archiveItem(Archive item, Date date) {
        item.setDateArchived(date);
        archives.add(item);
        log.append(String.format("Item %s archived at %s", item.getId(), date)).append("\n");
    }

    void openItem(int id, Date date) throws NonExistingItemException {
        Archive archive = archives.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NonExistingItemException(id));
        log.append(archive.openItem(date)).append("\n");
    }

    String getLog() {
        return log.toString();
    }
}

abstract class Archive {
    private int id;
    private Date dateArchived;

    public Archive(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Date getDateArchived() {
        return dateArchived;
    }

    public void setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
    }

    abstract public String openItem(Date date);
}

class LockedArchive extends Archive {
    private Date dateToOpen;

    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    @Override
    public String openItem(Date date) {
        if (date.before(dateToOpen))
            return String.format("Item %s cannot be opened before %s", getId(), dateToOpen);
        else
            return String.format("Item %s opened at %s", getId(), date);
    }
}

class SpecialArchive extends Archive {
    private int maxOpen;
    private int openedCount;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        this.openedCount = 0;
    }

    @Override
    public String openItem(Date date) {
        if (openedCount == maxOpen)
            return String.format("Item %s cannot be opened more than %d times", getId(), maxOpen);
        else {
            openedCount++;
            return String.format("Item %s opened at %s", getId(), date);
        }
    }
}
