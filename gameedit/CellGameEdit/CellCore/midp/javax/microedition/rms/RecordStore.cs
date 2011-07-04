namespace javax.microedition.rms
{

    /**
     * RecordStore 的摘要说明。
     */
    public class RecordStore
    {
        public int addRecord(byte[] data, int offset, int numBytes) { return 0; }
        public void addRecordListener(RecordListener listener) { }
        public void closeRecordStore() { }
        public void deleteRecord(int recordId) { }
        public static void deleteRecordStore(string recordStoreName) { }
        public long getLastModified() { return 0; }
        public string getName() { return ""; }
        public int getNextRecordID() { return 0; }
        public int getNumRecords() { return 0; }
        public byte[] getRecord(int recordId) { return null; }
        public int getRecord(int recordId, byte[] buffer, int offset) { return 0; }
        public int getRecordSize(int recordId) { return 0; }
        public int getSize() { return 0; }
        public int getSizeAvailable() { return 0; }
        public int getVersion() { return 0; }
        public static string[] listRecordStores() { return null; }
        public static RecordStore openRecordStore(string recordStoreName, bool createIfNecessary) { return null; }
        public static RecordStore openRecordStore(string recordStoreName, bool createIfNecessary, int authmode, bool writable) { return null; }
        public static RecordStore openRecordStore(string recordStoreName, string vendorName, string suiteName) { return null; }
        public void removeRecordListener(RecordListener listener) { }
        public void setMode(int authmode, bool writable) { }
        public void setRecord(int recordId, byte[] newData, int offset, int numBytes) { }


    }
}
