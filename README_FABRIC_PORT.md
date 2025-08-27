# Port do Mod Macros para Minecraft 1.21 (Fabric)

Este é um projeto de port do mod "Macro / Keybind Mod" da versão 1.12.1 (LiteLoader) para Minecraft 1.21 (Fabric).

## Status do Port

⚠️ **ATENÇÃO**: Este é um trabalho em andamento. O port não está completo e requer mais desenvolvimento.

### O que foi feito:

1. ✅ Estrutura básica do projeto Fabric criada
2. ✅ Arquivos de configuração (build.gradle, gradle.properties, fabric.mod.json)
3. ✅ Classe principal do mod adaptada para Fabric
4. ✅ Camada de compatibilidade criada
5. ✅ Primeiro mixin adaptado (MixinGuiChat)

### O que ainda precisa ser feito:

1. 🔄 **Adaptar todos os mixins restantes**:
   - MixinShapedRecipes
   - MixinShapelessRecipes
   - MixinEntityLivingBase
   - MixinSPacketCollectItem
   - MixinGameSettings
   - E todas as interfaces (IGuiEditSign, IGuiControls, etc.)

2. 🔄 **Atualizar mapeamentos de classes**:
   - As classes do Minecraft 1.12.1 usam nomes obfuscados (bib, bkn, etc.)
   - Precisam ser atualizadas para os nomes do Minecraft 1.21

3. 🔄 **Adaptar sistema de renderização**:
   - O sistema de renderização mudou significativamente
   - MatrixStack substituiu o sistema antigo
   - TextFieldWidget substituiu GuiTextField

4. 🔄 **Atualizar sistema de networking**:
   - Packet handling mudou completamente
   - Fabric usa um sistema diferente de networking

5. 🔄 **Adaptar sistema de eventos**:
   - LiteLoader events → Fabric events
   - Tick, render, input events precisam ser reescritos

6. 🔄 **Atualizar dependências**:
   - Verificar compatibilidade com Fabric API
   - Atualizar bibliotecas externas

## Como compilar e testar:

1. **Instalar dependências**:
   ```bash
   ./gradlew build
   ```

2. **Executar o mod**:
   ```bash
   ./gradlew runClient
   ```

## Estrutura do projeto:

```
minecraft-mod-macro/
├── build.gradle                    # Configuração do Gradle
├── gradle.properties              # Propriedades do projeto
├── fabric.mod.json               # Configuração do mod Fabric
├── macros.mixins.json            # Configuração dos mixins
├── net/eq2online/macros/
│   ├── fabric/                   # Código específico do Fabric
│   │   ├── MacrosFabricMod.java  # Classe principal
│   │   ├── compatibility/        # Camada de compatibilidade
│   │   └── mixin/               # Mixins adaptados
│   └── core/                    # Código original do mod
└── assets/                      # Recursos do mod
```

## Principais mudanças necessárias:

### 1. Mapeamentos de classes:
- `bib` → `MinecraftClient`
- `bkn` → `ChatScreen`
- `bje` → `TextFieldWidget`
- `blk` → `Screen`

### 2. Sistema de eventos:
- LiteLoader listeners → Fabric events
- Tickable → ClientTickEvents
- RenderListener → HudRenderCallback

### 3. Renderização:
- `drawScreen()` → `render(MatrixStack, int, int, float)`
- `GuiTextField` → `TextFieldWidget`

### 4. Networking:
- Packet handling → Fabric networking
- MessageBus → ServerPlayNetworking

## Próximos passos:

1. **Completar os mixins**: Adaptar todos os mixins restantes
2. **Testar compilação**: Resolver erros de compilação
3. **Testar funcionalidade**: Verificar se as funcionalidades básicas funcionam
4. **Otimizar**: Melhorar performance e compatibilidade

## Contribuição:

Se você quiser ajudar com o port, por favor:

1. Fork o repositório
2. Crie uma branch para sua feature
3. Faça commit das suas mudanças
4. Abra um Pull Request

## Licença:

Este projeto mantém a mesma licença do mod original.

---

**Nota**: Este é um projeto complexo que requer conhecimento profundo de:
- Minecraft modding
- Fabric API
- Java
- Mixin framework
- Diferenças entre versões do Minecraft

O port completo pode levar várias semanas ou meses de desenvolvimento.
