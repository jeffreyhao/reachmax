package com.github.bean.database;

import com.baidu.baselibrary.log.ALog;
import com.base.net.webhook.DingDingSender;
import com.github.bean.database.table.BookInfo;
import com.github.bean.database.table.CatalogList;
import com.github.bean.database.table.ReadHistory;
import com.github.bean.database.table.ReadingRecord;
import com.github.bean.database.table.RechargeFailOrder;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

/**
 * 数据库版本维护
 */
@Database(version = AppDatabase.VERSION)
public class AppDatabase {


    /**
     * 数据库版本
     */
    public static final int VERSION = 31;


    @Migration(version = 20, database = AppDatabase.class)
    public static class DatabaseAutoUpdate extends DbAutoUpdateMigration {
        // auto upgrade
    }

    @Migration(version = 21, database = AppDatabase.class)
    public static class Migration21 extends AlterTableMigration<CatalogList> {

        public Migration21(Class<CatalogList> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "comBookId");
        }
    }

    @Migration(version = 22, database = AppDatabase.class)
    public static class Migration22 extends AlterTableMigration<RechargeFailOrder> {

        public Migration22(Class<RechargeFailOrder> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "price");
        }
    }

    @Migration(version = 23, database = AppDatabase.class)
    public static class Migration23 extends AlterTableMigration<BookInfo> {

        public Migration23(Class<BookInfo> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "is_vip");
        }
    }

    @Migration(version = 24, database = AppDatabase.class)
    public static class Migration24 extends AlterTableMigration<ReadHistory> {

        public Migration24(Class<ReadHistory> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "is_vip");
        }
    }

    @Migration(version = 25, database = AppDatabase.class)
    public static class Migration25 extends AlterTableMigration<ReadingRecord> {

        public Migration25(Class<ReadingRecord> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "startFeeChapter");
        }
    }


    @Migration(version = 26, database = AppDatabase.class)
    public static class Migration26 extends BaseMigration {

        @Override
        public void migrate(DatabaseWrapper db) {
            db.beginTransaction();
            try {
                db.execSQL("ALTER TABLE ReadingRecord ADD COLUMN cid TEXT");

                // 数据迁移 chapterId -> cid
                db.execSQL("UPDATE ReadingRecord SET cid = chapterId");

                // clear chapterId
                db.execSQL("UPDATE ReadingRecord SET chapterId = ''");

                db.setTransactionSuccessful();
            } catch (Throwable e) {
                ALog.crash("Migration26", "migrate", e);
            } finally {
                db.endTransaction();
            }
        }
    }

    @Migration(version = 27, database = AppDatabase.class)
    public static class Migration27 extends AlterTableMigration<BookInfo> {

        public Migration27(Class<BookInfo> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "external_book_id");
        }
    }


    @Migration(version = 28, database = AppDatabase.class)
    public static class Migration28 extends AlterTableMigration<ReadingRecord> {

        public Migration28(Class<ReadingRecord> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "is_short_story");
        }
    }

    @Migration(version = 29, database = AppDatabase.class)
    public static class Migration29 extends AlterTableMigration<BookInfo> {

        public Migration29(Class<BookInfo> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "is_short_story");
            addColumn(SQLiteType.INTEGER, "billing_begin");
        }
    }

    /**
     * NovelDaily、NovelVault这两个包合并分支后，
     *      27 - Migration29
     *      28 - Migration28
     *  NovelDaily 缺少 Migration27 的执行，这里用 v30 补上 external_book_id。
     *
     *  注意：v30 对所有 oldVersion < 30 的用户都会触发，包括 NovelVault 老用户。
     *  这些用户在 v27 已经添加过 external_book_id 列，若直接 ALTER TABLE ADD COLUMN 会抛 SQLiteException: duplicate column name 导致崩溃。
     *  因此改为加列前先校验列是否存在，兼容两类用户。
     */
    @Migration(version = 31, database = AppDatabase.class)
    public static class Migration31 extends BaseMigration {

        @Override
        public void migrate(DatabaseWrapper db) {
            db.beginTransaction();
            try {
                if (!DbUtil.columnExists(db, "BookInfo", "external_book_id")) {
                    db.execSQL("ALTER TABLE BookInfo ADD COLUMN external_book_id INTEGER");
                }
                db.setTransactionSuccessful();
            } catch (Throwable e) {
                ALog.crash("Migration31", "migrate", e);
                DingDingSender.sendException(Thread.currentThread(), e, "Migration31.migrate()");
            } finally {
                db.endTransaction();
            }
        }


    }
}


