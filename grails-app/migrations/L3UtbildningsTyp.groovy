databaseChangeLog = {
    changeSet(author: "mano3567", id: "202110061800") {
        createTable(tableName: "l3utbildnings_typ") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "L3UtbildningsTypPK")
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

            column(name: "avser_tillfalle", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "benamning_en", type: "VARCHAR(128)") {
                constraints(nullable: "false")
            }

            column(name: "benamning_sv", type: "VARCHAR(128)") {
                constraints(nullable: "false")
            }

            column(name: "beskrivning_en", type: "VARCHAR(32)") {
                constraints(nullable: "true")
            }

            column(name: "beskrivning_sv", type: "VARCHAR(250)") {
                constraints(nullable: "true")
            }

            column(name: "edu", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "forvald_omfattning", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "grund_typ", type: "VARCHAR(32)") {
                constraints(nullable: "false")
            }

            column(name: "har_omfattning", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "individuell", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "kan_utannonseras", type: "BIT(1)") {
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

            column(name: "niva_inom_studieordning_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "sjalvstandig", type: "BIT(1)") {
                constraints(nullable: "false")
            }

            column(name: "slut_datum", type: "DATE") {
                constraints(nullable: "true")
            }

            column(name: "sorterings_ordning", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "start_datum", type: "DATE") {
                constraints(nullable: "true")
            }

            column(name: "studie_ordning_id", type: "INTEGER") {
                constraints(nullable: "false")
            }

            column(name: "tillatna_utbildningstyperistruktur", type: "VARCHAR(192)") {
                constraints(nullable: "true")
            }

            column(name: "tillfalle_inom_utbildningstyper", type: "VARCHAR(64)") {
                constraints(nullable: "true")
            }

            column(name: "versions_hanteras", type: "BIT(1)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "mano3567", id: "202110061830") {
        addUniqueConstraint(columnNames: "edu, ladok_id", constraintName: "UCEEDULID4UTYP", tableName: "l3utbildnings_typ")
    }
}
