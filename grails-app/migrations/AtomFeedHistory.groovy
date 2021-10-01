databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110011200") {
        createTable(tableName: "atom_feed_history") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "atomFeedHistoryPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "true")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "event", type: "TEXT") {
                constraints(nullable: "false")
            }

            column(name: "event_type", type: "VARCHAR(96)") {
                constraints(nullable: "false")
            }

            column(name: "event_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "feed_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "mano3567", id: "202110011230") {
        addUniqueConstraint(columnNames: "edu, event_uid", constraintName: "UCEDUEVENTAFH", tableName: "atom_feed_history")
    }
}
