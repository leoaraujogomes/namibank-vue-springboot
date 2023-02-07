<template>
  <v-card>
    <v-layout>
      <v-navigation-drawer class="bg-teal-darken-1" expand-on-hover rail>
        <v-list>
          <v-list-item
            prepend-avatar="/wave.ico"
            title="Nami Bank"
          ></v-list-item>
        </v-list>

        <v-divider />

        <v-list density="compact" nav>
          <RouterLink style="text-decoration: none; color: inherit" to="/"
            ><v-list-item
              prepend-icon="mdi-cash-fast"
              title="Realizar transferência"
              value="realizartransferencia"
            ></v-list-item
          ></RouterLink>
          <RouterLink
            style="text-decoration: none; color: inherit"
            to="/historico"
            ><v-list-item
              prepend-icon="mdi-history"
              title="Consultar histórico"
              value="historico"
            ></v-list-item
          ></RouterLink>
          <v-dialog v-model="dialog">
            <template v-slot:activator="{ props }">
              <v-list-item
                v-bind="props"
                prepend-icon="mdi-information"
                title="Entenda nossas taxas"
                value="taxas"
              ></v-list-item>
            </template>
            <v-card style="width: 515px; height: 300px; margin: auto">
              <v-card-text id="textoPopup"
                ><h3>
                  Possuímos duas modalidades de taxa, por data de agendamento e
                  por valor.
                </h3>
                <br />
                <h4>Taxas por data:</h4>
                <br />
                <strong>A</strong>: Tranferências para o mesmo dia do
                agendamento têm uma taxa de R$3 mais 3% do valor a ser
                transferido.<br />
                <strong>B</strong>: Tranferências até 10 dias da data de
                agendamento possuem uma taxa de R$12.<br />
                <strong>C</strong>: Operações do tipo C têm uma taxa regressiva
                conforme a data de transferência:<br />
                - Acima de 10 dias da data de agendamento: 8.2% <br />
                - Acima de 20 dias da data de agendamento: 6.9% <br />
                - Acima de 30 dias da data de agendamento: 4.7% <br />
                - Acima de 40 dias da data de agendamento: 1.7% <br /><br />
                <h4>Taxa por valor:</h4>
                <br />
                <strong>D</strong>: Operações do tipo D tem a taxa igual a A, B
                ou C dependendo do valor da transferência.<br /><br />
                - Valores até R$1.000 seguem a taxação tipo A.<br />
                - Valores de R$1.001 até R$2.000 seguem a taxação tipo B.<br />
                - Valores maiores que R$2.000 seguem a taxação tipo C.<br />
              </v-card-text>
              <v-card-actions>
                <v-btn color="primary" block @click="dialog = false"
                  >Fechar</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-list>
      </v-navigation-drawer>
    </v-layout>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,
      rules: [(value) => !!value || "Campo obrigatório."],
    };
  },
  //
};
</script>
