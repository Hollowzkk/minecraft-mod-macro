# Guia de Compilação - Mod Macros para Fabric

## Pré-requisitos

1. **Java 17 ou superior**
   ```bash
   java -version
   ```

2. **Gradle** (será baixado automaticamente pelo wrapper)

## Compilação

### 1. Primeira compilação (setup)

```bash
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

### 2. Executar o mod

```bash
# Windows
gradlew.bat runClient

# Linux/Mac
./gradlew runClient
```

### 3. Gerar JAR do mod

```bash
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

O arquivo JAR será gerado em: `build/libs/macros-0.15.4.jar`

## Estrutura do projeto após compilação

```
minecraft-mod-macro/
├── build/                    # Arquivos gerados pelo Gradle
│   ├── classes/             # Classes compiladas
│   ├── libs/               # JARs gerados
│   └── resources/          # Recursos processados
├── run/                    # Instância do Minecraft (após execução)
└── .gradle/               # Cache do Gradle
```

## Solução de problemas

### Erro: "Java version not found"
- Certifique-se de que o Java 17+ está instalado
- Configure a variável JAVA_HOME

### Erro: "Gradle wrapper not found"
- Execute: `gradle wrapper` (se você tem o Gradle instalado)
- Ou baixe o gradle-wrapper.jar manualmente

### Erro: "Fabric API not found"
- Verifique se a versão do Fabric API está correta no `gradle.properties`
- Execute: `gradlew --refresh-dependencies`

### Erro de compilação
- Verifique se todos os imports estão corretos
- Certifique-se de que as classes do Minecraft 1.21 estão sendo usadas
- Revise os mixins para compatibilidade

## Desenvolvimento

### Estrutura de desenvolvimento

```
net/eq2online/macros/
├── fabric/                 # Código específico do Fabric
│   ├── MacrosFabricMod.java
│   ├── compatibility/
│   └── mixin/
└── core/                  # Código original (precisa ser adaptado)
```

### Comandos úteis

```bash
# Limpar build
gradlew clean

# Atualizar dependências
gradlew --refresh-dependencies

# Ver dependências
gradlew dependencies

# Executar testes (se houver)
gradlew test
```

## Distribuição

Para distribuir o mod:

1. Compile o projeto: `gradlew build`
2. O JAR estará em: `build/libs/macros-0.15.4.jar`
3. Distribua o JAR junto com as dependências necessárias

### Dependências necessárias para o usuário final:

- Fabric Loader 0.15.0+
- Fabric API 0.91.0+
- Minecraft 1.21

## Notas importantes

⚠️ **Este é um port em desenvolvimento**
- Nem todas as funcionalidades estão implementadas
- Pode haver bugs e incompatibilidades
- Teste extensivamente antes de usar em produção

⚠️ **Compatibilidade**
- Este mod é específico para Minecraft 1.21
- Não é compatível com versões anteriores
- Pode não funcionar com outros mods

## Próximos passos

1. Completar a implementação dos mixins
2. Testar todas as funcionalidades
3. Otimizar performance
4. Adicionar suporte a mais versões do Minecraft
