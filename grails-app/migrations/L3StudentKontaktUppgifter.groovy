databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110081600") {
        createTable(tableName: "l3student_kontakt_uppgifter") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3StudentKontaktUppgifterPk")
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

            column(name: "epost_adress", type: "VARCHAR(96)") {
                constraints(nullable: "true")
            }

            column(name: "epost_adress_senast_andrad", type: "DATETIME") {
                constraints(nullable: "true")
            }

            column(name: "senast_sparad", type: "DATETIME") {
                constraints(nullable: "false")
            }

            column(name: "senast_andrad_av", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "student_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false", unique: "true")
            }

            column(name: "telefon_nummer", type: "VARCHAR(128)") {
                constraints(nullable: "true")
            }

            column(name: "telefon_nummer_senast_andrad", type: "DATETIME") {
                constraints(nullable: "true")
            }

            column(name: "uid", type: "VARCHAR(64)") {
                constraints(nullable: "false", unique: "true")
            }

        }

        createIndex(indexName: "student_uid4kontakt", tableName: "l3student_kontakt_uppgifter") {
            column(name: "student_uid")
        }

        addForeignKeyConstraint(baseColumnNames: "student_uid", baseTableName: "l3student_kontakt_uppgifter", constraintName: "stku2stud3FK", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "uid", referencedTableName: "l3student", referencesUniqueColumn: "true")
    }
}
