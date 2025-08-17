package com.example.leyan_hw1_countries;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Country.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
    private static volatile AppDatabase INSTANCE;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "countries_db"
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(populateCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback populateCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                CountryDao dao = INSTANCE.countryDao();
                dao.clear();

                dao.insertAll(
                        new Country("Palestine", "Jerusalem",
                                "Palestine is a land with profound historical and cultural significance, considered the cradle of civilizations and home to holy sites of the three Abrahamic religions. It has a deep-rooted heritage, rich traditions, and a resilient people who have preserved their identity despite challenges.",
                                R.drawable.palestine),

                        new Country("Egypt", "Cairo",
                                "Egypt, known as the gift of the Nile, is one of the oldest civilizations in the world. From the majestic pyramids of Giza to the treasures of the pharaohs, it has been a center of culture, art, and science for millennia. Modern Egypt blends its ancient heritage with vibrant contemporary life.",
                                R.drawable.egypt),

                        new Country("Qatar", "Doha",
                                "Qatar is a rapidly developing Gulf country that has transformed from a pearl-diving nation into a modern hub of business, culture, and sports. It is known for its futuristic skyline, cultural landmarks like Katara, and hosting major international events such as the FIFA World Cup 2022.",
                                R.drawable.qatar),

                        new Country("Turkey", "Ankara",
                                "Turkey is a bridge between Europe and Asia, blending Eastern traditions with Western influences. It is famous for the historic city of Istanbul, the cultural richness of Anatolia, and breathtaking landscapes from Cappadocia to the Mediterranean coast. Turkey is a center of history, trade, and modern development.",
                                R.drawable.turkey),

                        new Country("Kuwait", "Kuwait City",
                                "Kuwait is a small but wealthy Gulf country with vast oil resources that have fueled its modern growth. Known for its iconic Kuwait Towers, cultural museums, and rich Arabian traditions, it is a country where heritage and modernity meet in a dynamic way.",
                                R.drawable.kuwait),

                        new Country("Saudi Arabia", "Riyadh",
                                "Saudi Arabia is the largest country in the Arabian Peninsula and the heartland of Islam, home to the holy cities of Mecca and Medina. In addition to its spiritual significance, it has launched ambitious projects such as Vision 2030 to diversify its economy and enhance tourism.",
                                R.drawable.saudi_arabia),

                        new Country("Lebanon", "Beirut",
                                "Lebanon, often called the Switzerland of the Middle East, is famous for its breathtaking mountains, Mediterranean coast, and vibrant culture. Beirut, its capital, is known for its resilience, cuisine, art, and nightlife. Lebanon carries a deep history that mixes Phoenician, Roman, Arab, and Ottoman influences.",
                                R.drawable.lebanon),

                        new Country("Syria", "Damascus",
                                "Syria is home to Damascus, one of the oldest continuously inhabited cities in the world. Rich in history, Syria has ancient castles, Roman ruins, and cultural landmarks. Despite recent challenges, it remains a land of great heritage, poetry, and traditions.",
                                R.drawable.syria),

                        new Country("Jordan", "Amman",
                                "Jordan is a Middle Eastern country known for its hospitality, safety, and world-famous sites. From the rose-red city of Petra to the healing waters of the Dead Sea, Jordan combines natural wonders with historical depth, making it a unique destination in the Arab world.",
                                R.drawable.jordan)
                );
            });
        }
    };
}
