databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110041045") {
        createTable(tableName: "l3finansierings_form") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3FinansieringsformPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "true")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "true")
            }

            column(name: "benamning_en", type: "VARCHAR(128)") {
                constraints(nullable: "false")
            }

            column(name: "benamning_sv", type: "VARCHAR(128)") {
                constraints(nullable: "false")
            }

            column(name: "beskrivning_en", type: "VARCHAR(128)") {
                constraints(nullable: "true")
            }

            column(name: "beskrivning_sv", type: "VARCHAR(512)") {
                constraints(nullable: "true")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "kod", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "ladok_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "larosate_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "slut_datum", type: "DATE") {
                constraints(nullable: "true")
            }

            column(name: "start_datum", type: "DATE") {
                constraints(nullable: "true")
            }
        }
    }
}
