databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110041900") {
        createTable(tableName: "l3period") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3PeriodPK")
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

            column(name: "benamning_en", type: "VARCHAR(64)") {
                constraints(nullable: "true")
            }

            column(name: "benamning_sv", type: "VARCHAR(64)") {
                constraints(nullable: "false")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "from_datum", type: "DATE") {
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

            column(name: "period_typ_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "tom_datum", type: "DATE") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "mano3567", id: "202110041930") {
        addUniqueConstraint(columnNames: "edu, kod", constraintName: "UCEEDUKOD4PERIOD", tableName: "l3period")
    }
}
