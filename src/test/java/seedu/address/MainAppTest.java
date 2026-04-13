package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

public class MainAppTest {

    @TempDir
    public Path temporaryFolder;

    @Test
    public void setPastMeetingNotification_noRemovedMeetings_noNotification() throws Exception {
        MainApp mainApp = createMainApp();
        invokeSetPastMeetingNotification(mainApp, new JsonAddressBookStorage(temporaryFolder.resolve("ab.json")));

        assertNull(getStartupNotification(mainApp));
    }

    @Test
    public void setPastMeetingNotification_removedMeetings_setsNotification() throws Exception {
        MainApp mainApp = createMainApp();
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(temporaryFolder.resolve("ab.json")) {
            @Override
            public int getRemovedPastMeetingCount() {
                return 2;
            }
        };

        invokeSetPastMeetingNotification(mainApp, addressBookStorage);

        assertEquals("2 past meeting(s) removed.", getStartupNotification(mainApp));
    }

    @Test
    public void setPastMeetingNotification_saveFails_setsNotification() throws Exception {
        MainApp mainApp = createMainApp();
        setField(mainApp, "storage", new StorageManager(
                new JsonAddressBookStorage(temporaryFolder.resolve("ab.json")) {
                    @Override
                    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
                        throw new IOException("save failed");
                    }
                },
                new JsonUserPrefsStorage(temporaryFolder.resolve("prefs.json"))));
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(temporaryFolder.resolve("ab.json")) {
            @Override
            public int getRemovedPastMeetingCount() {
                return 1;
            }
        };

        invokeSetPastMeetingNotification(mainApp, addressBookStorage);

        assertEquals("1 past meeting(s) removed.", getStartupNotification(mainApp));
    }

    private MainApp createMainApp() throws Exception {
        MainApp mainApp = new MainApp();
        setField(mainApp, "model", new ModelManager());
        setField(mainApp, "storage", new StorageManager(
                new JsonAddressBookStorage(temporaryFolder.resolve("ab.json")),
                new JsonUserPrefsStorage(temporaryFolder.resolve("prefs.json"))));
        return mainApp;
    }

    private void invokeSetPastMeetingNotification(MainApp mainApp, JsonAddressBookStorage addressBookStorage)
            throws Exception {
        Method method = MainApp.class.getDeclaredMethod("setPastMeetingNotification", JsonAddressBookStorage.class);
        method.setAccessible(true);
        method.invoke(mainApp, addressBookStorage);
    }

    private void setField(MainApp mainApp, String fieldName, Object value) throws Exception {
        Field field = MainApp.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(mainApp, value);
    }

    private String getStartupNotification(MainApp mainApp) throws Exception {
        Field field = MainApp.class.getDeclaredField("startupNotification");
        field.setAccessible(true);
        return (String) field.get(mainApp);
    }
}
