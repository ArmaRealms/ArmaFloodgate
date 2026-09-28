package org.geysermc.floodgate.database;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.logger.FloodgateLogger;
import org.geysermc.floodgate.config.FloodgateConfig;
import org.geysermc.floodgate.util.InjectorHolder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Guards the Guice 6 injection path used by PlayerLinkHolder on Velocity. */
class SqliteDatabaseInjectionTest {
    @TempDir
    Path dataDirectory;

    @Test
    void guiceProvidesDataDirectoryBeforeDatabaseIsLoaded() {
        final FloodgateConfig config = new FloodgateConfig() {
            @Override
            public PlayerLinkConfig getPlayerLink() {
                return new PlayerLinkConfig();
            }
        };
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Path.class).annotatedWith(Names.named("dataDirectory"))
                        .toInstance(dataDirectory);
                bind(FloodgateConfig.class).toInstance(config);
                bind(FloodgateApi.class).toInstance(unusedService(FloodgateApi.class));
                bind(FloodgateLogger.class).toInstance(unusedService(FloodgateLogger.class));
                bind(InjectorHolder.class).toInstance(new InjectorHolder());
            }
        });

        final SqliteDatabase database = injector.getInstance(SqliteDatabase.class);
        try {
            database.load();
            assertTrue(Files.isRegularFile(dataDirectory.resolve("linked-players.db")));
        } finally {
            database.stop();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T unusedService(final Class<T> type) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type},
                (proxy, method, args) -> {
                    throw new AssertionError("Unexpected test service call: " + method.getName());
                });
    }
}
