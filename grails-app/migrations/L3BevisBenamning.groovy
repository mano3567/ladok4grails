databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110040530") {
        createTable(tableName: "l3bevis_benamning") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3BevisBenamningPK")
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

            column(name: "benamning_en", type: "VARCHAR(192)") {
                constraints(nullable: "false")
            }

            column(name: "benamning_sv", type: "VARCHAR(192)") {
                constraints(nullable: "false")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "giltighets_period_slut_datum", type: "DATE") {
                constraints(nullable: "true")
            }

            column(name: "kod", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "omfattning", type: "DECIMAL(6,1)") {
                constraints(nullable: "false")
            }

            column(name: "uid", type: "VARCHAR(64)") {
                constraints(nullable: "false", unique: "true")
            }
        }
    }

    changeSet(author: "mano3567", id: "202110040550") {
        addUniqueConstraint(columnNames: "edu, kod", constraintName: "UCEEDUKOD4BB", tableName: "l3bevis_benamning")
    }
}
