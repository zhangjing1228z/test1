# Python for 循环示例

# 示例 1: 遍历数字范围
print("示例 1: 遍历数字范围")
for i in range(5):
    print(f"当前数字: {i}")

print("\n" + "="*30 + "\n")

# 示例 2: 遍历列表
print("示例 2: 遍历列表")
fruits = ["苹果", "香蕉", "橙子", "葡萄"]
for fruit in fruits:
    print(f"我喜欢吃{fruit}")

print("\n" + "="*30 + "\n")

# 示例 3: 带索引的遍历
print("示例 3: 带索引的遍历")
for index, fruit in enumerate(fruits):
    print(f"{index + 1}. {fruit}")

print("\n" + "="*30 + "\n")

# 示例 4: 嵌套循环
print("示例 4: 嵌套循环 - 乘法表")
for i in range(1, 6):
    for j in range(1, 6):
        print(f"{i} × {j} = {i*j:2d}", end="  ")
    print()
