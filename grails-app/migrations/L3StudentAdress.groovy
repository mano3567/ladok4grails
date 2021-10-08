databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110081300") {
        createTable(tableName: "l3student_adress") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3StudentAdressPk")
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

            column(name: "care_of", type: "VARCHAR(128)") {
                constraints(nullable: "true")
            }
            column(name: "land", type: "VARCHAR(64)") {
                constraints(nullable: "true")
            }
            column(name: "post_adress_typ", type: "VARCHAR(32)") {
                constraints(nullable: "false")
            }
            column(name: "post_nummer", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }
            column(name: "post_ort", type: "VARCHAR(96)") {
                constraints(nullable: "false")
            }
            column(name: "senast_andrad", type: "DATE") {
                constraints(nullable: "false")
            }
            column(name: "student_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }
            column(name: "utdelnings_adress", type: "VARCHAR(128)") {
                constraints(nullable: "false")
            }
        }

        createIndex(indexName: "student_uid4adress", tableName: "l3student_adress") {
            column(name: "student_uid")
        }

        addUniqueConstraint(columnNames: "student_uid, post_adress_typ", constraintName: "UCSTPAT", tableName: "l3student_adress")

        addForeignKeyConstraint(baseColumnNames: "student_uid", baseTableName: "l3student_adress", constraintName: "stad2stud3FK", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "uid", referencedTableName: "l3student", referencesUniqueColumn: "true")
    }
}
