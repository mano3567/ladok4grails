databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110311800") {
        createTable(tableName: "l3avbrott") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3AvbrottPK")
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

            column(name: "anteckning", type: "VARCHAR(512)") {
                constraints(nullable: "true")
            }

            column(name: "avbrotts_datum", type: "DATE") {
                constraints(nullable: "false")
            }

            column(name: "edu", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "extern_referens", type: "VARCHAR(512)") {
                constraints(nullable: "true")
            }

            column(name: "student_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "utbildning_uid", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "mano3567", id: "202110311900") {
        addUniqueConstraint(columnNames: "student_uid, utbildning_uid", constraintName: "UCSTUDUTB4AVBR", tableName: "l3avbrott")
    }
}
