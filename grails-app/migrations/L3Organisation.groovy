databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110041220") {
        createTable(tableName: "l3organisation") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3OrganisationPK")
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
                constraints(nullable: "true")
            }

            column(name: "benamning_sv", type: "VARCHAR(128)") {
                constraints(nullable: "false")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "giltighets_period_slut_datum", type: "DATE") {
                constraints(nullable: "true")
            }

            column(name: "organisations_enhets_typ", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "organisations_kod", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "uid", type: "VARCHAR(64)") {
                constraints(nullable: "false", unique: "true")
            }
        }
    }

    changeSet(author: "mano3567", id: "202110041225") {
        addUniqueConstraint(columnNames: "edu, organisations_kod", constraintName: "UCEEDUKOD4ORG", tableName: "l3organisation")
    }
}
